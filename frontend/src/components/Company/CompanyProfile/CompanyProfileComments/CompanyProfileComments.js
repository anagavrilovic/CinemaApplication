import React from 'react';
import StarRate from '../../../StarRate/StarRate';
import classes from './CompanyProfileComments.module.css';

function CompanyProfileComments({comments}) {

    return (
        <div className={classes.comments}>
            {comments.map((c) => {
                return <div key={c.id} className={classes.comment}>
                    <div className={classes.commentHeader}>
                        <div className={classes.commentRate}>
                            <p className={classes.rate}>{c.mark}</p>
                            <StarRate rate={c.mark}/>
                        </div>
                        <div className={classes.caption}>
                            <h3 className={classes.captionText}>{c.caption}</h3>
                            <p className={classes.date}>{new Date(c.creationDate).toLocaleDateString()}</p>
                        </div>
                    </div>
                    <div className={classes.commentContent}>
                        <p className={classes.commentText}>{c.text}</p>
                        <p className={classes.authorInfo}>{c.user.firstName} {c.user.lastName}</p>
                        <p className={classes.authorInfo}>{c.currentlyWorking ? "Currently working in this company" : "Not working anymore in this company"}</p>
                    </div>
                </div>
            })}
        </div>
    )
}

export default CompanyProfileComments