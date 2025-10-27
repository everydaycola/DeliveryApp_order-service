import {submitOrder} from "../ts/api/orderApi.ts";

const params = new URLSearchParams(window.location.search);
const restaurantId = params.get("restaurantId");
const orderId = params.get("orderId") as string;

export function renderCheckoutPage() {
    const params = new URLSearchParams(window.location.search);
    const restaurantId = params.get("restaurantId");
    const orderId = params.get("orderId");

    if (!restaurantId || !orderId) {
        document.getElementById("app")!.innerHTML =
            `<div class="alert alert-danger p-3">No order found</div>`;
        return;
    }

    document.getElementById("app")!.innerHTML = `
        <div class="container py-4">
            <h2>Checkout</h2>

            <form id="checkoutForm">
                <div class="mb-3">
                  <label class="form-label">Naam</label>
                  <input type="text" id="name" class="form-control" required>
                </div>

                <div class="mb-3">
                  <label class="form-label">Leveradres</label>
                  <input type="text" id="address" class="form-control" required>
                </div>

                <div class="mb-3">
                  <label class="form-label">Contact E-mail</label>
                  <input type="email" id="email" class="form-control" required>
                </div>

                <button type="submit" class="btn btn-primary" id="checkoutBtn">
                  Plaats bestelling
                </button>
            </form>

            <div id="finalDetails" class="mt-4 d-none">
                <h5>Bestelling bevestigd</h5>
                <p id="finalText"></p>
            </div>
        </div>
    `;

    const form = document.getElementById("checkoutForm")!;
    const finalBlock = document.getElementById("finalDetails")!;
    const finalText = document.getElementById("finalText")!;

    form.addEventListener("submit", (ev) => {
        ev.preventDefault();

        const name = (document.getElementById("name") as HTMLInputElement).value;
        const address = (document.getElementById("address") as HTMLInputElement).value;
        const email = (document.getElementById("email") as HTMLInputElement).value;

        form.querySelectorAll("input").forEach(i => i.setAttribute("disabled", "true"));
        const btn = document.getElementById("checkoutBtn") as HTMLButtonElement;
        btn.disabled = true;

        finalText.innerHTML = `
            Naam: ${name}<br>
            Leveradres: ${address}<br>
            E-mail: ${email}<br>
            ☑️ Bestelling is nu definitief!
        `;

        finalBlock.classList.remove("d-none");
    });
}


async function submitNewOrder(){
    await submitOrder(orderId)
}