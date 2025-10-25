import axios from "axios";
import type {Restaurant} from "../model/restaurant";
import type {Dish} from "../model/dish/Dish.ts";
import type {Order, OrderLine} from "../model/order";

const BASE_URL = 'http://localhost:8080/api/';

export async function getRestaurants(){
        const response = await axios.get<Restaurant[]>(`${BASE_URL}restaurants`);
        return response.data
}

export async function getMenuOfRestaurant(restaurantId: string){
    const response = await axios.get<Dish[]>(`${BASE_URL}restaurants/${restaurantId}/menu`);
    return response.data
}

export async function createOrder(restaurantId: string){
    const response = await axios.post<Order>(`${BASE_URL}orders`, {restaurantId});
    return response.data
}

export async function addOrderLineToOrder(orderId: string, dishId :string, orderLine: OrderLine){
    const response = await axios.post<Order>(`${BASE_URL}orders/${orderId}/dishes/${dishId}`, orderLine);
    return response.data
}