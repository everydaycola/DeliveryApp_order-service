import {getDish, getOrder} from "../ts/api/orderApi.ts";
import type {Dish} from "../ts/model/dish/Dish.ts";
import {OrderStatus} from "../ts/model/order";
import {renderStatusTracker} from "../ts/orderTracking";

export async function renderProgressPage() {
    const params = new URLSearchParams(globalThis.location.search);
    const restaurantId = params.get("restaurantId");
    const orderId = params.get("orderId");

    if (!restaurantId || !orderId) {
        document.getElementById("app")!.innerHTML =
            `<div class="alert alert-danger p-3">No order found</div>`;
        return;
    }

    const order = await getOrder(orderId)
    const dishes: Dish[] = await Promise.all(order.orderLines.map(ol => getDish(restaurantId, ol.dishId)));

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

            <div id="finalDetails" class="mt-4">
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

    const finalText = document.getElementById("finalText")!;

    const name = order.contactInfo.name;
    const address = order.contactInfo.address;
    const email = order.contactInfo.contactEmail;

    finalText.innerHTML = `
    Name: ${name}<br>
    Address: ${address}<br>
    E-mail: ${email}<br>
    ✅ Your order has been placed!
    `;

    const statusContainer = document.getElementById("orderStatusTracker")!;

    statusContainer.innerHTML = renderStatusTracker(order.status);

    if (order.status === OrderStatus.DECLINED) {
        statusContainer.innerHTML = `
        <div class="container py-4">
            <div class="container py-4">
                <div class="alert alert-danger">
                    ❌ Your order was declined.<br>
                    <strong>Reason: </strong>${order.comment ? order.comment : 'No specific reason provided.'}
                </div>
            </div>
        </div>
`;
    }

    const refreshInterval = 10000; // Refresh every 10 seconds
    const statusRefreshInterval = setInterval(updateStatusTracker, refreshInterval);

    async function updateStatusTracker() {
        console.log("Updating status tracker");
        const updatedOrder = await getOrder(order.orderId);
        if (updatedOrder.status !== order.status) {
            order.status = updatedOrder.status;
            statusContainer.innerHTML = renderStatusTracker(order.status);

            if (order.status === OrderStatus.DECLINED) {
                statusContainer.innerHTML = `
                    <div class="container py-4">
                        <div class="container py-4">
                            <div class="alert alert-danger">
                                ❌ Your order was declined.<br>
                                <strong>Reason: </strong>${order.comment ? order.comment : 'No specific reason provided.'}
                            </div>
                        </div>
                    </div>`;
                clearInterval(statusRefreshInterval); // Stop refreshing if the order is declined
            }
        }
    }
}
