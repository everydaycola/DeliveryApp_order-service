import type {Orderline} from "./Orderline.ts";

export type Order = {
    id: string
    restaurantId: string
    status: string
    orderLines: Orderline[]
}