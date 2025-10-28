import type {OrderLine} from "./OrderLine.ts";

export type Order = {
    orderId: string
    restaurantId: string
    status: OrderStatusType
    orderLines: OrderLine[]
}

export const OrderStatus = {
    UNCONFIRMED: "UNCONFIRMED",
    PENDING: "PENDING",
    ACCEPTED: "ACCEPTED",
    DECLINED: "DECLINED",
    READY: "READY",
    IN_DELIVERY: "IN_DELIVERY",
    DELIVERED: "DELIVERED",
} as const;

export type OrderStatusType = typeof OrderStatus[keyof typeof OrderStatus];

export const OrderSteps: OrderStatusType[] = [
    OrderStatus.UNCONFIRMED,
    OrderStatus.PENDING,
    OrderStatus.ACCEPTED,
    OrderStatus.READY,
    OrderStatus.IN_DELIVERY,
    OrderStatus.DELIVERED,
];