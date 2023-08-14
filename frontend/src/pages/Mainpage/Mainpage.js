import Login from "../../components/Login/Login";
import Registration from "../../components/RegistrationComponents/Registration/Registration";
import classes from './Mainpage.module.css';

import { useState } from 'react';

function Mainpage() {
    const [isLoginPage, setIsLoginPage] = useState(true);
    const [isRegisterPage, setIsRegisterPage] = useState(false);

    function navigateToLogin() {
        setIsLoginPage(true);
        setIsRegisterPage(false);
    }

    function navigateToRegister() {
        setIsLoginPage(false);
        setIsRegisterPage(true);
    }

    return (
        <div className={classes.page}>
            { isLoginPage ? <Login navigateToRegister={navigateToRegister} /> : null }
            { isRegisterPage ? <Registration navigateToLogin={navigateToLogin} /> : null}
        </div>
    );
}

export default Mainpage;