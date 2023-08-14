import * as yup from "yup";

const nameRegex = /^[a-zA-ZšŠđĐžŽčČćĆ ]+$/;
const mediumPasswordRegex = /^(((?=.*[a-z])(?=.*[A-Z]))|((?=.*[a-z])(?=.*[0-9]))|((?=.*[A-Z])(?=.*[0-9])))(?=.{6,})/;
const strongPasswordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{10,})/;

const schema = yup.object().shape({
    firstName: 
        yup.string()
        .required("First name is required.")
        .matches(nameRegex, "First name should be letters only."),
    lastName: 
        yup.string()
        .required("Last name is required.")
        .matches(nameRegex, "Last name should be letters only."),
    email: 
        yup.string()
        .email("Invalid e-mail address.")
        .required("Email is required."),
    username: 
        yup.string()
        .required("Username is required."),
    password:  
        yup.string()
        .required("Password is required.")
        .matches(mediumPasswordRegex, "Password is too weak.")
        .matches(strongPasswordRegex, "Password has medium strength."),
    confirmPassword: 
        yup.string()
        .oneOf([yup.ref("password")], "Passwords do not match.")
        .required("Please confirm your password.")
});

export default schema;