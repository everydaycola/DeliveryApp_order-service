import type { Restaurant } from "../model/restaurant";
import {showRestaurantDetailModal} from "../restaurantDetail";


function createCell(content: string | HTMLElement): HTMLTableCellElement {
    const td = document.createElement("td");
    if (typeof content === "string") {
        td.textContent = content;
    } else {
        td.appendChild(content);
    }
    return td;
}

export function renderRestaurantTable(container: HTMLElement, restaurants: Restaurant[]): void {
    container.innerHTML = ""; // Clear container

    const table = document.createElement("table");
    table.className = "table table-striped table-hover table-bordered w-75"; // Bootstrap styling
    table.id = "restaurants-table"

    // Header
    const thead = document.createElement("thead");
    thead.className = "table-dark"; // Dark header
    const headerRow = document.createElement("tr");
    ["Name", "Type", "Open", "Price"].forEach(h => {
        const th = document.createElement("th");
        th.textContent = h;
        headerRow.appendChild(th);
    });
    thead.appendChild(headerRow);
    table.appendChild(thead);

    // Body
    const tbody = document.createElement("tbody");
    restaurants.forEach(resto => {
        const row = document.createElement("tr");
        row.appendChild(createCell(resto.name));
        row.appendChild(createCell(resto.type));
        row.appendChild(createCell(resto.isOpen ? "✅" : "❌"));
        row.appendChild(createCell(resto.priceCriteria));

        row.addEventListener('click',() => showRestaurantDetailModal(resto))
        tbody.appendChild(row);
    });

    table.appendChild(tbody);
    container.appendChild(table);
}
