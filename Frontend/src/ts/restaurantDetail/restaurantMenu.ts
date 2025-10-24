import {getMenuOfRestaurant} from "../api/orderApi.ts";

export async function showMenu(restautantId: string){
    const menuDiv = document.getElementById("restaurantMenu") as HTMLDivElement
    const menu = await getMenuOfRestaurant(restautantId)

    //menu.forEach()
}