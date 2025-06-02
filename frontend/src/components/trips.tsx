import api from "../api/axios.ts";

async function DisplayTrips() {
    try {
        const response = await api.get('/trip/', {headers : {"Authorization" : `Bearer ${localStorage.getItem("authToken")}`}})
        console.log(response)
    } catch (error) {
        console.error('Trip error:', error);
        return false;
    }
    return true;
}

export default DisplayTrips;