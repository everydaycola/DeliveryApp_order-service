import {getMenuOfRestaurant} from "../api/orderApi.ts";
import type {Dish} from "../model/Dish/Dish.ts";

export async function showMenu(restautantId: string){
    const menuDiv = document.getElementById("restaurantMenu") as HTMLDivElement
    const menu = await getMenuOfRestaurant(restautantId)
    menuDiv.innerHTML = ""

    menu.forEach(dish => menuDiv.append(createDishCard(dish)))
}

export function createDishCard(dish: Dish): HTMLElement {
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
            
            <div class="d-flex justify-content-between align-items-center ">
                <button class="btn btn-outline-secondary btn-sm minus-btn">-</button>
                <span class="quantity fw-bold mx-3">0</span>
                <button class="btn btn-outline-secondary btn-sm plus-btn">+</button>
            </div>          
        </div>
    `;

    return card;
}