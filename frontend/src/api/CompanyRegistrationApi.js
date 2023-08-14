import { axiosInstance } from "./AxiosInstance";

export async function getAllRegistrationRequests() {
    const response = await axiosInstance.get('company-registration');
    return response.data;
}

export async function approveRequest(requestId) {
    const response = await axiosInstance.get(`company-registration/approve/${requestId}`);
    return response.data;
}

export async function rejectRequest(requestId) {
    const response = await axiosInstance.get(`company-registration/reject/${requestId}`);
    return response.data;
}