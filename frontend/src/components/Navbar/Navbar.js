import { Link } from "react-router-dom";
import classes from "./Navbar.module.css";
import { CheckUserPermission } from '../../components/Permissions/CheckUserPermission.js';

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBars } from "@fortawesome/free-solid-svg-icons";
import { faRightFromBracket } from "@fortawesome/free-solid-svg-icons";
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons'

import { useState, useEffect } from "react";
import { useDispatch } from "react-redux";
import { useLocation } from 'react-router';
    
import { logout } from "../../features/user";

function Navbar() {
    var [mobileNavClicked, setMobileNavClicked] = useState(false);

    const dispatch = useDispatch();
    const location = useLocation();

    function handleLogOut() {
        dispatch(logout());
    }

    return (
        <div>
            {mobileNavClicked ? (<div className={classes.backdrop} onClick={() => setMobileNavClicked(false)} ></div>) : null}

            <nav className={classes.nav}>
                <div className={classes.navLogo}>
                    <Link to="/home" id="home_tab" className={classes.logoLink}>
                        <span className={classes.logo}>C</span>
                        <span className={classes.logoText}>inescape</span>
                    </Link>
                    <div className={`${classes.formItem} ${location.pathname === "/home" ? classes.formItemSearchDarkTheme : classes.formItemSearchLightTheme}`}>
                        <FontAwesomeIcon icon={faMagnifyingGlass} className={classes.icon} />
                        <input type='text' placeholder='Search movies...' />
                    </div>
                </div>
                <div>
                    <Link to="/movies" id="movies_tab" className={`${classes.link} ${location.pathname === "/home" ? classes.linkDarkTheme : classes.linkLightTheme}`}>
                        Movies
                    </Link>
                    <Link to="/theaters" id="theaters_tab" className={`${classes.link} ${location.pathname === "/home" ? classes.linkDarkTheme : classes.linkLightTheme}`}>
                        Theaters
                    </Link>
                    <Link to="/projections" id="projections_tab" className={`${classes.link} ${location.pathname === "/home" ? classes.linkDarkTheme : classes.linkLightTheme}`}>
                        Projections
                    </Link>
                    <Link to="/reservations" id="reservations_tab" className={`${classes.link} ${location.pathname === "/home" ? classes.linkDarkTheme : classes.linkLightTheme}`}>
                        Reservations
                    </Link>
                    <CheckUserPermission role="['ROLE_ADMIN']">
                        <Link to="/users" id="users_tab" className={`${classes.link} ${location.pathname === "/home" ? classes.linkDarkTheme : classes.linkLightTheme}`} >
                            Users
                        </Link>
                    </CheckUserPermission>
                    
                    <Link to="/" id="logout_tab" className={`${classes.link} ${location.pathname === "/home" ? classes.linkDarkTheme : classes.linkLightTheme}`}>
                        <span className={classes.logout}>
                            <span className={classes.username}>{JSON.parse(localStorage.getItem("user")).username}</span>
                            <FontAwesomeIcon icon={faRightFromBracket} onClick={handleLogOut} />
                        </span>
                    </Link>
                    <FontAwesomeIcon icon={faBars} 
                        className={`${classes.menuIcon} ${location.pathname === "/home" ? classes.menuIconDarkTheme : classes.menuIconLightTheme}`} 
                        onClick={() => setMobileNavClicked(true)} />
                </div>
            </nav>

            {mobileNavClicked ? (
                <nav className={classes.mobileNav}>
                    <div className={classes.mobileNavLinks}>
                        <Link to="/home" id="home_tab" className={classes.mobileLink} onClick={() => setMobileNavClicked(false)} >
                            Homepage
                        </Link>
                        <Link to="/movies" id="movies_tab" className={classes.mobileLink} onClick={() => setMobileNavClicked(false)} >
                            Movies
                        </Link>
                        <Link to="/theaters" id="theaters_tab" className={classes.mobileLink} onClick={() => setMobileNavClicked(false)}  >
                            Theaters
                        </Link>
                        <Link to="/projections" id="projections_tab" className={classes.mobileLink} onClick={() => setMobileNavClicked(false)}  >
                            Projections
                        </Link>
                        <Link to="/reservations" id="reservations_tab" className={classes.mobileLink} onClick={() => setMobileNavClicked(false)}  >
                            Reservations
                        </Link>
                        <CheckUserPermission role="['ROLE_ADMIN']">
                            <Link to="/users" id="users_tab" className={classes.mobileLink} onClick={() => setMobileNavClicked(false)}  >
                                Users
                            </Link>
                        </CheckUserPermission>
                        <Link to="/" id="logout_tab" className={classes.mobileLink}>
                            LogOut <FontAwesomeIcon icon={faRightFromBracket} />
                        </Link>
                    </div>
                </nav>
            ) : null}
        </div>
    );
}

export default Navbar;
