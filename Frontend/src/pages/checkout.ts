import {getDish, getOrder, submitOrder} from "../ts/api/orderApi.ts";
import type {Dish} from "../ts/model/dish/Dish.ts";

export async function renderCheckoutPage() {
    const params = new URLSearchParams(window.location.search);
    const restaurantId = params.get("restaurantId");
    const orderId = params.get("orderId");

    if (!restaurantId || !orderId) {
        document.getElementById("app")!.innerHTML =
            `<div class="alert alert-danger p-3">No order found</div>`;
        return;
    }

    const order = await getOrder(orderId)
    const dishes: Dish[] = await Promise.all(order.orderLines.map(ol => getDish(restaurantId,ol.dishId)));

    const total = dishes.reduce(
        (sum, d, currentIndex) => sum + d.price * order.orderLines[currentIndex].amount,
        0);

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

            <form id="checkoutForm">
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

                <button type="submit" class="btn btn-primary" id="checkoutBtn">
                  Place order
                </button>
            </form>

            <div id="finalDetails" class="mt-4 d-none">
                <h5>Order Placed</h5>
                <p id="finalText"></p>
                <div id="orderStatusTracker" class="mt-4"></div>
            </div>
        </div>
    `;

    // Fill the list
    const list = document.getElementById("dishList")!;
    dishes.forEach((d, index) => {
        const li = document.createElement("li");
        li.className = "list-group-item d-flex justify-content-between align-items-center";
        li.innerHTML = `
            <div>
                <strong>${d.name}</strong><br>
                <small>€${d.price.toFixed(2)} × ${order.orderLines[index].amount}</small>
            </div>
            <span class="badge bg-secondary">€${(d.price * order.orderLines[index].amount).toFixed(2)}</span>
        `;
        list.appendChild(li);
    });

    const form = document.getElementById("checkoutForm")!;

    form.addEventListener("submit", async (ev) => {
        ev.preventDefault();

        const name = (document.getElementById("name") as HTMLInputElement).value;
        const address = (document.getElementById("address") as HTMLInputElement).value;
        const email = (document.getElementById("email") as HTMLInputElement).value;

        await submitNewOrder(orderId, name, address, email);

        globalThis.location.href = `/progress?restaurantId=${restaurantId}&orderId=${orderId}`;

    });
}


async function submitNewOrder(orderId: string, name: string, address: string, email: string){
    await submitOrder(orderId, name, address, email)
}