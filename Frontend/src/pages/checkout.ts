import {getDish, getOrder, submitOrder} from "../ts/api/orderApi.ts";
import type {Dish} from "../ts/model/dish/Dish.ts";
import {loadStripe, type Stripe} from "@stripe/stripe-js";
import type {OrderLine} from "../ts/model/order";

let stripe: Stripe | null = null;

async function initStripe() {
    stripe = await loadStripe("pk_test_51SNqJPAwP2c5LObC0xL6XWjtSC1AqKZlTVzyxD9a5NTb6zOuHkptB76zFmIvwSKIucrmVQm47s9Z4olT20FH0Cfk0017SaDwa4");
}

let elements;

export async function renderCheckoutPage() {
    const params = new URLSearchParams(window.location.search);
    const restaurantId = params.get("restaurantId");
    const orderId = params.get("orderId");

    await initStripe()

    if (!restaurantId || !orderId) {
        document.getElementById("app")!.innerHTML =
            `<div class="alert alert-danger p-3">No order found</div>`;
        return;
    }

    const order = await getOrder(orderId);
    const dishes: Dish[] = await Promise.all(order.orderLines.map(ol => getDish(restaurantId, ol.dishId)));

    const total = dishes.reduce(
        (sum, d, idx) => sum + d.price * order.orderLines[idx].amount,
        0
    );

    document.getElementById("app")!.innerHTML = `
        <div class="container py-4">
            <h2>Checkout</h2>
            
            <div class="card shadow-sm mb-4">
                <div class="card-body">
                    <h5 class="card-title">Ordered Dishes</h5>
                    <ul class="list-group list-group-flush" id="dishList"></ul>
                    <div class="mt-3 text-end fw-bold fs-5">
                        Total: €${total.toFixed(2)}
                    </div>
                </div>
            </div>

            <form id="payment-form">
                <div class="mb-3">
                    <label class="form-label">Name</label>
                    <input type="text" id="name" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Address</label>
                    <input type="text" id="address" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Contact E-mail</label>
                    <input type="email" id="email" class="form-control" required>
                </div>

                <div id="payment-element"><!--Stripe.js injects the Payment Element--></div>

                <button type="submit" class="btn btn-primary" id="submit">Pay now</button>
                <div id="payment-message" class="hidden mt-2"></div>
            </form>

            <div id="finalDetails" class="mt-4 d-none">
                <h5>Order Placed</h5>
                <p id="finalText"></p>
                <div id="orderStatusTracker" class="mt-4"></div>
            </div>
        </div>
    `;

    // Fill the ordered dishes list
    const list = document.getElementById("dishList")!;
    dishes.forEach((d, idx) => {
        const li = document.createElement("li");
        li.className = "list-group-item d-flex justify-content-between align-items-center";
        li.innerHTML = `
            <div>
                <strong>${d.name}</strong><br>
                <small>€${d.price.toFixed(2)} × ${order.orderLines[idx].amount}</small>
            </div>
            <span class="badge bg-secondary">€${(d.price * order.orderLines[idx].amount).toFixed(2)}</span>
        `;
        list.appendChild(li);
    });

    // Initialize Stripe Payment Element
    await initializePaymentElement(order.orderLines, restaurantId);

    const form = document.getElementById("checkoutForm")!;

    form.addEventListener("submit", async (ev) => {
        ev.preventDefault();
        setLoading(true);

        const name = (document.getElementById("name") as HTMLInputElement).value;
        const address = (document.getElementById("address") as HTMLInputElement).value;
        const email = (document.getElementById("email") as HTMLInputElement).value;

        // Confirm Stripe Payment
        const {error} = await stripe.confirmPayment({
            elements,
            confirmParams: {
                return_url: window.location.href // stay on the page for demo purposes
            }
        });

        if (error) {
            showMessage(error.message || "Payment failed.");
            setLoading(false);
            return;
        }

        // Disable form after successful payment
        form.querySelectorAll("input, button").forEach(el => (el as HTMLInputElement | HTMLButtonElement).disabled = true);

        await submitNewOrder(orderId, name, address, email);

        globalThis.location.href = `/progress?restaurantId=${restaurantId}&orderId=${orderId}`;

        setLoading(false);
    });
}

async function initializePaymentElement(orderLines: any[], restaurantId:string) {
    let body = await generateItemsJson(orderLines, restaurantId)
    console.log(body)
    const response = await fetch("http://localhost:4242/create-payment-intent", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: body
    });
    const {clientSecret} = await response.json();

    const appearance = {theme: "stripe"};
    elements = stripe.elements({appearance, clientSecret});

    const paymentElementOptions = {layout: "accordion"};
    const paymentElement = elements.create("payment", paymentElementOptions);
    paymentElement.mount("#payment-element");
}

// Show loading spinner during payment
function setLoading(isLoading: boolean) {
    const submitBtn = document.getElementById("submit") as HTMLButtonElement;
    submitBtn.disabled = isLoading;
}

// Display message under form
function showMessage(message: string) {
    const msg = document.getElementById("payment-message")!;
    msg.classList.remove("hidden");
    msg.textContent = message;
    setTimeout(() => {
        msg.classList.add("hidden");
        msg.textContent = "";
    }, 5000);
}

async function submitNewOrder(orderId: string, name: string, address: string, email: string){
    await submitOrder(orderId, name, address, email)
}

async function generateItemsJson(orderLines: OrderLine[], restaurantId: string) {
    const items: { id: string; amount: number }[] = [];

    for (const orderline of orderLines) {
        const dish = await getDish(restaurantId, orderline.dishId);
        for (let i = 0; i < orderline.amount; i++) {
            items.push({ id: dish.id, amount: dish.price*100 });
        }
    }

    return JSON.stringify({ items });
}
