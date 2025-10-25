import axios from "axios";
import type {Restaurant} from "../model/restaurant";
import type {Dish} from "../model/Dish/Dish.ts";

const BASE_URL = 'http://localhost:8080/api/';

export async function getRestaurants(){
        const response = await axios.get<Restaurant[]>(`${BASE_URL}restaurants`);
        return response.data
}

export async function getMenuOfRestaurant(restaurantId: string){
    const response = await axios.get<Dish[]>(`${BASE_URL}restaurants/${restaurantId}/menu`);
    return response.data
}