import '../style/style.scss'
import {getRestaurants} from "./api/orderApi.ts";
import {renderRestaurantTable} from "./restaurantTable";
import {renderCheckoutPage} from "../pages/checkout.ts";
import {renderProgressPage} from "../pages/progress.ts";

const restaurants = getRestaurants()

function handleNavClick(e: MouseEvent) {
    const target = e.target as HTMLAnchorElement;

    if (target.tagName === "A" && target.getAttribute("href")?.startsWith("/")) {
        e.preventDefault();
        history.pushState({}, "", target.href);
        route();
    }
}

async function route() {
    const appDiv = document.getElementById("app") as HTMLDivElement

    const path = globalThis.location.pathname;

    if (path === "/" || path === "/home") {
        renderRestaurantTable(appDiv,await restaurants);
    }
    else if (path === "/checkout") {
        await renderCheckoutPage();
    } else if (path === "/progress") {
        await renderProgressPage();
    }
    else {
         appDiv.innerHTML = `
            <div class="container py-4">
                <h2>404 - Pages not found</h2>
                <a href="/" class="btn btn-primary mt-3">Back to Home</a>
            </div>
        `;
    }
    console.log("loaded");
}

document.addEventListener("click", handleNavClick);
globalThis.addEventListener("popstate", route);

await route();





