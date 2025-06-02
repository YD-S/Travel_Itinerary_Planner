import api from '../api/axios.ts';
import { useEffect, useRef } from 'react';

function DisplayTrips() {
    const called = useRef(false);

    useEffect(() => {
        if (called.current) return;
        called.current = true;

        const fetchTrips = async () => {
            try {
                const response = await api.get('api/trip/', {
                    headers: { "Authorization": `Bearer ${localStorage.getItem("authToken")}` }
                });
                console.log(response.data);
            } catch (error) {
                console.error('Error fetching trips:', error);
            }
        };
        fetchTrips();
    }, []);

    return null;
}

export default DisplayTrips;