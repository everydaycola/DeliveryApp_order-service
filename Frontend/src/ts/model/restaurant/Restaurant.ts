import type {Address} from "./Address.ts";
import type {RestaurantOpeningHours} from "./RestaurantOpeningHours.ts";

export type Restaurant = {
    id: string
    name: string
    address: Address
    contactEmail: string
    type: RestaurantType
    openingHours: RestaurantOpeningHours[]
    logo: string
    isOpen: boolean
    priceCriteria: PriceCriteria
}

export type RestaurantType =
    | "FASTFOOD"
    | "ITALIAN"
    | "JAPANESE"
    | "AMERICAN"
    | "SANDWICHSHOP";



export type PriceCriteria =
    | "€"
    | "€€"
    | "€€€"
    | "€€€€"
    | "?";


export type RestaurantWithoutOpeningHours = Omit<Restaurant, 'openingHours'>