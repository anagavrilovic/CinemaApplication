import classes from './Registration.module.css';

import RegisterUser from '../RegisterUser/RegisterUser';

function Registration(props) {

    return (
        <div className={classes.register}>
            <h1 className={classes.caption}>
               Register now!
            </h1>

            <RegisterUser goToLogin={() => props.navigateToLogin()}/>

            <a href="/#" className={classes.registerLink} onClick={() => props.navigateToLogin()}>
                Already have an account? Log in here!
            </a>
        </div>
    );
}

export default Registration;