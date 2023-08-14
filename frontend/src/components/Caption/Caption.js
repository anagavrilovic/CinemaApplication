import classes from "./Caption.module.css";

function Caption(props) {
    return (
        <div className={classes.page}>
            <h1 className={classes.caption}>{props.caption}</h1>
            <div className={classes.underline}></div>
        </div>
    );
}

export default Caption;