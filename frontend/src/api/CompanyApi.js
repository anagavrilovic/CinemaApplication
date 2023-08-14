import { axiosInstance } from "./AxiosInstance";

export async function getAllCompanies() {
    const response = await axiosInstance.get('company/all');
    return response.data;
}

export async function getCommentsByCompany(companyId) {
    const response = await axiosInstance.get(`comment/all/${companyId}`);
    return response.data;
}

export async function getSalariesByCompany(companyId) {
    const response = await axiosInstance.get(`salary/all/${companyId}`);
    return response.data;
}

export async function getInterviewsByCompany(companyId) {
    const response = await axiosInstance.get(`interview/all/${companyId}`);
    return response.data;
}

export async function addComment(newComment) {
    const response = await axiosInstance.post('comment', newComment);
    return response;
}

export async function getPositionsByCompany(companyId) {
    const response = await axiosInstance.get(`position/all/${companyId}`);
    return response.data;
}

export async function addSalaryInfo(newSalary) {
    const response = await axiosInstance.post('salary', newSalary);
    return response;
}

export async function addInterviewInfo(newInterview) {
    const response = await axiosInstance.post('interview', newInterview);
    return response;
}