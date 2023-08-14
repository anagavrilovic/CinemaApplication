import React from 'react';
import classes from './RegisterUser.module.css';

import { useState } from 'react';
import { useForm } from "react-hook-form";
import { yupResolver } from '@hookform/resolvers/yup';
import schema from "../../../validationSchemas/RegisterUserValidationSchema";
import { axiosInstance } from "../../../api/AxiosInstance"

function RegisterUser(props) {

    const { register, handleSubmit, formState: { errors } } = useForm({
        resolver: yupResolver(schema)
    });

    const [serverError, setServerError] = useState(false);

    function registrationHandler(data) {

        const registrationRequest = {
            firstName: data.firstName,
            lastName: data.lastName,
            email: data.email,
            username: data.username, 
            password: data.password,
            role: "USER"
        }

        console.log(registrationRequest);

        axiosInstance.post("auth/register", registrationRequest)
            .then((response) => {
                props.goToLogin();
                console.log(response.data);
            })
            .catch((error) => {
                if (error.response.data.message.includes("already exists")) {
                    setServerError(true);
                }
            })
    }


    return (
        <div>
            <form onSubmit={handleSubmit(registrationHandler)} className={classes.form}>

                <div className={classes.formItem}>
                    <input type='text' placeholder='First Name'
                        className={errors.firstName ? classes.errorInput : ''}
                        {...register("firstName")} />
                    <div className={classes.errorMessage}>{errors.firstName?.message}</div>
                </div>

                <div className={classes.formItem}>
                    <input type='text' placeholder='Last Name'
                        className={errors.lastName ? classes.errorInput : ''}
                        {...register("lastName")} />
                    <div className={classes.errorMessage}>{errors.lastName?.message}</div>
                </div>

                <div className={classes.formItem}>
                    <input type='text' placeholder='Email'
                        className={errors.email || serverError ? classes.errorInput : ''}
                        {...register("email")} />
                    <div className={classes.errorMessage}>{errors.email?.message}</div>
                </div>

                <div className={classes.formItem}>
                    <input type='text' placeholder='Username'
                        className={errors.username || serverError ? classes.errorInput : ''}
                        {...register("username")} />
                    <div className={classes.errorMessage}>{errors.username?.message}</div>
                </div>

                <div className={`${classes.formItem} ${classes.password}`}>
                    <input type='password' placeholder='Password'
                        className={`${errors.password?.message === 'Password is too weak.' || errors.password?.message === 'Password is required.' ? classes.errorInput : ''}
                    ${errors.password?.message === 'Password has medium strength.' ? classes.mediumStrengthPassword : ''}
                    ${!errors?.password && false ? classes.passwordStrong : ''}`}
                        {...register("password")} />
                    <div className={classes.tooltip}>Strong password must be at least 10 characters long, including at least 1 uppercase letter, 1 lowercase letter, 1 numeric character and 1 special character.</div>
                    <div className={`${classes.errorMessage} 
                    ${errors.password?.message === 'Password has medium strength.' ? classes.errorMessagePasswordWeak : ''}`}>{errors.password?.message}</div>
                </div>

                <div className={classes.formItem}>
                    <input type='password' placeholder='Confirm Password'
                        className={errors.confirmPassword ? classes.errorInput : ''}
                        {...register("confirmPassword")} />
                    <div className={classes.errorMessage}>{errors.confirmPassword?.message}</div>
                </div>

                <div className={classes.serverErrorMessage}>
                        {serverError ? "Account with this e-mail or username already exists." : ""}</div>
                <button className={classes.buttonLogIn}>Register</button>
            </form>
        </div>
    )
}

export default RegisterUser