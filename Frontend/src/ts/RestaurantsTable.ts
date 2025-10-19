import type { Restaurant } from "./model/Restaurant.ts";

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
    table.className = "table table-striped table-hover table-bordered"; // Bootstrap styling

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
    restaurants.forEach(r => {
        const row = document.createElement("tr");
        row.appendChild(createCell(r.name));
        row.appendChild(createCell(r.type));
        row.appendChild(createCell(r.isOpen ? "✅" : "❌"));
        row.appendChild(createCell(r.priceCriteria));
        tbody.appendChild(row);
    });

    table.appendChild(tbody);
    container.appendChild(table);
}
