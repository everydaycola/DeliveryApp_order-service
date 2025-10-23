import type {Restaurant} from "../model/restaurant";
import {Modal} from "bootstrap";

export function showRestaurantDetailModal(resto: Restaurant): void {
    const title = document.getElementById("restaurantDetailTitle");
    const body = document.getElementById("restaurantDetailBody") as HTMLDivElement;
    const modalEl = document.getElementById("restaurantDetailModal");
    const typeBadge = document.getElementById("typeBadge") as HTMLSpanElement

    if (!title || !body || !modalEl) return;

    title.textContent = resto.name;
    typeBadge.textContent = resto.type
    renderRestaurantInfoDiv(resto)

    const modal = Modal.getOrCreateInstance(modalEl);
    modal.show();
}

function renderRestaurantInfoDiv(resto: Restaurant) {
    const infoDiv = document.getElementById("restaurantInfo") as HTMLDivElement

    const email = createInfoP("Email", resto.contactEmail)


    infoDiv.appendChild(email)

}

function createInfoP(key: string, value: string) {
    const paragraph = document.createElement("p")
    paragraph.innerHTML = `<strong>${key}: </strong> ${value}`
    return paragraph
}