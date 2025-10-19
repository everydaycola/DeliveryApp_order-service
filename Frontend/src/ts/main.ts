import '../style/style.css'
import {getRestaurants} from "./api/orderApi.ts";
import {renderRestaurantTable} from "./RestaurantsTable.ts";

const restaurants = getRestaurants()

const appDiv = document.querySelector<HTMLDivElement>('#app') as HTMLDivElement

renderRestaurantTable(appDiv,await restaurants)



