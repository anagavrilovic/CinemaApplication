import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { useState } from "react";
import { login } from "../../features/user"

import classes from "./Login.module.css";
import { axiosInstance } from "../../api/AxiosInstance"


function Login(props) {
    const [error, setError] = useState(false);

    const dispatch = useDispatch();
    const navigate = useNavigate();

    
    function submitHandler(event) {
        event.preventDefault();
        
        const credentials = {
            email: event.target[0].value,
            password: event.target[1].value
        }
        
        axiosInstance.post("auth/login", credentials, {
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
            },
        })
        .then((response) => {
            dispatch(login(response.data));
            localStorage.setItem('token',response.data.accessToken);
            navigate("/home");
        })
        .catch(() => {
            setError(true);
        })
    }

    return (
        <div className={classes.login}>
            <h1 id="caption" className={classes.caption}>Log in</h1>
            <form onSubmit={submitHandler} className={classes.form}>
                <div className={classes.errorMessage}> {error ? "Wrong email or password! Try again." : ""}</div>

                <div className={classes.formItem}>
                    <input type="text" id="username_input" required placeholder="Email" onChange={() => setError(false)} />
                </div>
                <div className={classes.formItem}>
                    <input type="password" id="password_input" required placeholder="Password" onChange={() => setError(false)} />
                </div>

                <button id="login_button" className={classes.buttonLogIn}>Log in</button>
                <a href="/#" className={classes.registerLink} onClick={() => props.navigateToRegister()} >
                    Don't have an account? Register here.
                </a>
            </form>
        </div>
    );
}

export default Login;
