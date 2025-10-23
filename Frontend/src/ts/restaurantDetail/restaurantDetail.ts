import type {Restaurant} from "../model/restaurant";
import {Modal} from "bootstrap";

export function showRestaurantDetailModal(resto: Restaurant): void {
    const title = document.getElementById("restaurantDetailTitle");
    const body = document.getElementById("restaurantDetailBody") as HTMLDivElement;
    const modalEl = document.getElementById("restaurantDetailModal");

    if (!title || !body || !modalEl) return;

    title.textContent = resto.name;
    renderRestaurantInfoDiv(resto)

    const modal = Modal.getOrCreateInstance(modalEl);
    modal.show();
}

function renderRestaurantInfoDiv(resto: Restaurant){
    const infoDiv = document.getElementById("restaurantInfo") as HTMLDivElement

    infoDiv.innerHTML = `
        <p><strong>Type:</strong> ${resto.type.toLocaleLowerCase()}</p>
        <p><strong>Open:</strong> ${resto.isOpen ? "✅" : "❌"}</p>
        <p><strong>Price:</strong> ${resto.priceCriteria}</p>
    `;

}