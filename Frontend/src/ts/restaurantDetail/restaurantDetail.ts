import type {Restaurant} from "../model/restaurant";
import {Modal} from "bootstrap";
import type {RestaurantOpeningHours} from "../model/restaurant/RestaurantOpeningHours.ts";
import {showMenu} from "./restaurantMenu.ts";

export function showRestaurantDetailModal(resto: Restaurant): void {
    const title = document.getElementById("restaurantDetailTitle");
    const body = document.getElementById("restaurantDetailBody") as HTMLDivElement;
    const modalEl = document.getElementById("restaurantDetailModal");
    const typeBadge = document.getElementById("typeBadge") as HTMLSpanElement

    if (!title || !body || !modalEl) return;

    title.textContent = resto.name;
    typeBadge.textContent = resto.type
    fillRestaurantInfoDiv(resto)
    showMenu(resto.id, resto.isOpen)

    const modal = Modal.getOrCreateInstance(modalEl);
    modal.show();
}

function fillRestaurantInfoDiv(resto: Restaurant) {
    const emailAnchor = document.getElementById("restaurantEmail") as HTMLAnchorElement
    const addressSpan = document.getElementById("restaurantAddress") as HTMLSpanElement
    const openSpan = document.getElementById("restaurantOpen") as HTMLSpanElement

    emailAnchor.textContent = resto.contactEmail
    emailAnchor.href = `mailto:${resto.contactEmail}`

    addressSpan.textContent =
        `${resto.address.street} ${resto.address.number} ${resto.address.postalCode} in ${resto.address.country}`

    openSpan.className = resto.isOpen? "badge bg-success" : "badge bg-warning"
    openSpan.textContent = openTextGenerator(resto.isOpen,resto.openingHours)

}

function formatTime(time: string): string {
    return time.slice(0, 5); // keeps "HH:MM"
}

function openTextGenerator(open: boolean, openingHours: RestaurantOpeningHours[]): string {
    const daysOfWeek = [
        "SUNDAY",
        "MONDAY",
        "TUESDAY",
        "WEDNESDAY",
        "THURSDAY",
        "FRIDAY",
        "SATURDAY",
    ] as const;

    const now = new Date();
    const nowDayName = daysOfWeek[now.getDay()];
    const today = openingHours.find(h => h.day === nowDayName);

    if (!today) return "Closed today";

    const openTime = formatTime(today.openingTime);
    const closeTime = formatTime(today.closingTime);

    const [openHour, openMinute] = openTime.split(":").map(Number);
    const nowMinutes = now.getHours() * 60 + now.getMinutes();
    const openMinutes = openHour * 60 + openMinute;

    if (open) {
        return `Open until ${closeTime}`;
    } else if (nowMinutes < openMinutes) {

        return `Closed — opens today at ${openTime}`;
    } else {
        // Find next open day
        for (let i = 1; i <= 7; i++) {
            const nextDay = daysOfWeek[(now.getDay() + i) % 7];
            const next = openingHours.find(h => h.day === nextDay);
            if (next) return `Closed — opens ${nextDay.toLowerCase()} at ${formatTime(next.openingTime)}`;
        }
        return "Closed";
    }
}