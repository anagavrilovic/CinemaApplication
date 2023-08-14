import React from 'react';
import classes from './CompanyProfile.module.css';
import { useLocation } from 'react-router-dom';
import Synechron from '../../../../images/synechron.png';
import StarRate from '../../../StarRate/StarRate';
import { useState, useEffect } from 'react';
import CompanyProfileNavigation from '../CompanyProfileNavigation/CompanyProfileNavigation';
import CompanyProfileAbout from '../CompanyProfileAbout/CompanyProfileAbout';
import CompanyProfileComments from '../CompanyProfileComments/CompanyProfileComments';
import CompanyProfileSalary from '../CompanyProfileSalary/CompanyProfileSalary';
import CompanyProfileInterview from '../CompanyProfileInterview/CompanyProfileInterview';
import CompanyProfileJobs from '../CompanyProfileJobs/CompanyProfileJobs';
import AddComment from '../CompanyProfileComments/AddComment/AddComment';
import { getCommentsByCompany, getInterviewsByCompany, getSalariesByCompany } from '../../../../api/CompanyApi';
import AddSalary from '../CompanyProfileSalary/AddSalary/AddSalary';
import AddInterview from '../CompanyProfileInterview/AddInterview/AddInterview';
import { CheckUserPermission } from '../../../Permissions/CheckUserPermission.js';


function CompanyProfile() {

    const location = useLocation();
    const [activeTab, setactiveTab] = useState('about');
    const [addCommentVisible, setAddCommentVisible] = useState(false);
    const [addSalaryVisible, setAddSalaryVisible] = useState(false);
    const [addInterviewVisible, setAddInterviewVisible] = useState(false);
    const [comments, setComments] = useState([]);
    const [salaries, setSalaries] = useState([]);
    const [interviews, setInterviews] = useState([]);

    const company = location.state.company;

    function toggleAddCommentModal() {
        setAddCommentVisible(!addCommentVisible);
    }

    function toggleAddSalaryModal() {
        setAddSalaryVisible(!addSalaryVisible);
    }

    function toggleAddInterviewModal() {
        setAddInterviewVisible(!addInterviewVisible);
    }

    async function reloadComments(){
        const results = await getCommentsByCompany(company.id);
        setComments(results);
    }

    async function reloadSalaries(){
        const results = await getSalariesByCompany(company.id);
        setSalaries(results);
    }

    async function reloadInterviews(){
        const results = await getInterviewsByCompany(company.id);
        setInterviews(results);
    }

    useEffect(() => {
        async function getComments(){
            const results = await getCommentsByCompany(company.id);
            setComments(results);
        }

        async function getSalaries(){
            const results = await getSalariesByCompany(company.id);
            setSalaries(results);
        }

        async function getInterviews(){
            const results = await getInterviewsByCompany(company.id);
            setInterviews(results);
        }

        getInterviews();
        getSalaries();
        getComments();
    }, [company])

    return (
        <div>
            <div className={classes.cover}></div>
            <div className={classes.page}>
                <div className={classes.mainInfoWrapper}>
                    <div className={classes.mainInfo}>
                        <img src={Synechron} alt={"Company logo"} className={classes.profilePhoto} />
                        <div className={classes.profileInfo}>
                            <h2 className={classes.companyName}>{company.name}</h2>
                            <div className={classes.stars}>
                                <StarRate rate={location.state.companyRate} />
                                <p className={classes.rate}>{location.state.companyRate}</p>
                            </div>
                            <p className={classes.avgRate}>(average rate)</p>
                        </div>
                    </div>

                    {activeTab === 'comments' ? <CheckUserPermission role="['ROLE_USER']"><button className={classes.button} onClick={toggleAddCommentModal}>Leave a comment</button></CheckUserPermission> : null}
                    {activeTab === 'salary' ? <CheckUserPermission role="['ROLE_USER']"><button className={classes.button} onClick={toggleAddSalaryModal}>Leave a salary information</button></CheckUserPermission> : null}
                    {activeTab === 'interview' ? <CheckUserPermission role="['ROLE_USER']"><button className={classes.button} onClick={toggleAddInterviewModal}>Leave an interview impression</button></CheckUserPermission> : null}
                </div>

                <CompanyProfileNavigation activeTab={activeTab} setactiveTab={(tab) => setactiveTab(tab)} />

                {activeTab === 'about' ? <CompanyProfileAbout company={company} /> : null}
                {activeTab === 'comments' ? <CompanyProfileComments comments={comments} /> : null}
                {activeTab === 'salary' ? <CompanyProfileSalary companyId={company.id} salaries={salaries} /> : null}
                {activeTab === 'interview' ? <CompanyProfileInterview comments={interviews} /> : null}
                {activeTab === 'jobs' ? <CompanyProfileJobs companyEmail={company.email} /> : null}
            </div>

            {addCommentVisible && <AddComment toggleAddComment={toggleAddCommentModal} company={company} reload={reloadComments}/>}
            {addSalaryVisible && <AddSalary toggleAddSalary={toggleAddSalaryModal} company={company} reload={reloadSalaries}/>}
            {addInterviewVisible && <AddInterview toggleAddInterview={toggleAddInterviewModal} company={company} reload={reloadInterviews}/>}
        </div>
    )
}

export default CompanyProfile