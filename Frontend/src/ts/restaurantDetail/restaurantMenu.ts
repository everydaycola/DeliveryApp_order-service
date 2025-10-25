import {addOrderLineToOrder, createOrder, getMenuOfRestaurant} from "../api/orderApi.ts";
import type {Dish} from "../model/dish/Dish.ts";
import type {OrderLine} from "../model/order";

const orderLines:OrderLine[] = []

export async function showMenu(restautantId: string){
    const menuDiv = document.getElementById("restaurantMenu") as HTMLDivElement
    const menu = await getMenuOfRestaurant(restautantId)
    menuDiv.innerHTML = ""

    menu.forEach(dish => menuDiv.appendChild(createDishCard(dish)))

    const buttonWrapper = document.createElement("div");
    buttonWrapper.className = "d-flex justify-content-end mt-3 w-100";

    const submitBtn = document.createElement("button");
    submitBtn.textContent = `Checkout`;
    submitBtn.className = "btn btn-success btn";

    submitBtn.addEventListener("click",()=> submitOrder(restautantId))

    buttonWrapper.appendChild(submitBtn)
    menuDiv.appendChild(buttonWrapper);
}

function createDishCard(dish: Dish): HTMLElement {
    const card = document.createElement("div");
    card.className = "card shadow-sm m-2 w-100";
    card.style.width = "18rem";

    card.innerHTML = `
        <div class="card-body d-flex flex-row align-items-center justify-content-between">
            <div class="w-50">
                <h5 class="card-title">${dish.name}</h5>
                <p class="card-text text-muted">${dish.description}</p>
            </div>
            
            <p class="fw-bold">€${dish.price.toFixed(2)}</p>
            
            <div class="d-flex justify-content-between align-items-center">
                <button class="btn btn-outline-secondary btn-sm minus-btn">-</button>
                <span class="quantity fw-bold mx-3">0</span>
                <button class="btn btn-outline-secondary btn-sm plus-btn">+</button>
            </div>          
        </div>
    `;

    const minusBtn = card.querySelector(".minus-btn") as HTMLButtonElement;
    const plusBtn = card.querySelector(".plus-btn") as HTMLButtonElement;
    const quantityEl = card.querySelector(".quantity") as HTMLElement;

    let quantity = 0;

    plusBtn.addEventListener("click", () => {
        quantity++;
        quantityEl.textContent = String(quantity);
        addOrderLine(dish.id,quantity)
        console.log(orderLines)
    });

    minusBtn.addEventListener("click", () => {
        if (quantity > 0) {
            quantity--;
            quantityEl.textContent = String(quantity);
            addOrderLine(dish.id,quantity)
            console.log(orderLines)
        }
    });

    return card;
}

function addOrderLine(dishId: string, amount: number){
    const orderLine = orderLines.find((orderLine) => orderLine.dishId == dishId)
    if (orderLine){
        orderLine.amount = amount
    } else {
        orderLines.push({dishId,amount})
    }
}

async function submitOrder(restaurantId: string){
    const order = await createOrder(restaurantId)
    orderLines.forEach(ol => addOrderLineToOrder(order.id, ol.dishId, ol))
}