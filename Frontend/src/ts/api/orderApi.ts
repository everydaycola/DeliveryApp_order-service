import axios from "axios";
import type {Restaurant} from "../model/Restaurant.ts";

const BASE_URL = 'http://localhost:8080/api/';

export async function getRestaurants(){
        const response = await axios.get<Restaurant[]>(`${BASE_URL}restaurants`);
        return response.data
}