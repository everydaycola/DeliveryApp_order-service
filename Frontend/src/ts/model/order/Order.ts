import type {OrderLine} from "./OrderLine.ts";

export type Order = {
    id: string
    restaurantId: string
    status: string
    orderLines: OrderLine[]
}