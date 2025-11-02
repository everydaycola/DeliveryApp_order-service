import express from "express";
import Stripe from "stripe";
import cors from "cors";


const app = express();
const stripe = new Stripe("sk_test_51SNqJPAwP2c5LObCgFMmMmYvk58aAXnPKP1WIa6wrhzVvOX3ftCHxiAJE41BbQNfll6FTuEzWvwt0CZejB3UAVz000Miv3XkNz", {
    apiVersion: "2024-06-20",
});

app.use(cors({
    origin: "http://localhost:5173",
}));
app.use(express.json());

const calculateOrderAmount = (items) => {
    let total = 0;
    items.forEach((item) => {
        console.log(item)
        total += item.amount;
    });
    return total;
};

app.post("/create-payment-intent", async (req, res) => {
    const { items } = req.body;

    const paymentIntent = await stripe.paymentIntents.create({
        amount: calculateOrderAmount(items),
        currency: "eur",
        automatic_payment_methods: { enabled: true },
    });

    res.send({
        clientSecret: paymentIntent.client_secret,
    });
});

app.listen(4242, () => console.log("Node server listening on port 4242!"));
