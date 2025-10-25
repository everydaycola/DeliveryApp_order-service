import type {OrderLine} from "./OrderLine.ts";

export type Order = {
    orderId: string
    restaurantId: string
    status: string
    orderLines: OrderLine[]
}