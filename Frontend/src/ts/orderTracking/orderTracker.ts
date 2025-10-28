import {type OrderStatusType, OrderSteps} from "../model/order";
import "./orderTracker.scss"

export function renderStatusTracker(status: OrderStatusType) {
    const currentIndex = OrderSteps.indexOf(status);

    return `
    <div class="steps d-flex justify-content-between">
        ${OrderSteps.map((step, index) => `
            <div class="step text-center flex-fill">
                <div class="circle ${index <= currentIndex ? "completed" : ""}"></div>
                <small class="text-capitalize">${step.replace("_", " ")}</small>
            </div>
        `).join("")}
    </div>
    `;
}

