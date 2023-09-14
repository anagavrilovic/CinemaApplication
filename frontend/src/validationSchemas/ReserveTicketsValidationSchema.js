import * as yup from "yup";

const schema = yup.object().shape({
    numberOfTickets: 
        yup.number()
        .required("Number of tickets is required.")
});

export default schema;