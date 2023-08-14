import { createSlice } from "@reduxjs/toolkit";

const initialStateValue = {
    accessToken: "",
    email: "",
    username: "",
    role: ""
};

export const userSlice = createSlice({
    name: "user",
    initialState: { value: localStorage.getItem("user") == null ? initialStateValue : JSON.parse(localStorage.getItem("user")) },

    reducers: {
        login: (state, action) => {
            state.value = action.payload;
            localStorage.setItem("user", JSON.stringify(action.payload));
        },

        logout: (state) => {
            state.value = initialStateValue;
            localStorage.setItem("user", JSON.stringify(initialStateValue));
        },
    },
});

// to get userconst user = useSelector((state) => state.user.value);

export const { login, logout } = userSlice.actions;

export default userSlice.reducer;