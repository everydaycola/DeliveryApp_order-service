import '../style/style.scss'
import {getRestaurants} from "./api/orderApi.ts";
import {renderRestaurantTable} from "./restaurantTable";

const restaurants = getRestaurants()

const appDiv = document.querySelector<HTMLDivElement>('#app') as HTMLDivElement

renderRestaurantTable(appDiv,await restaurants)



