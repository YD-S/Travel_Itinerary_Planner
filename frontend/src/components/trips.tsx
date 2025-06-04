import type { AxiosResponse } from 'axios';
import api from '../api/axios.ts';
import {useEffect, useRef} from 'react';

function DisplayTrips() {
    const called = useRef(false);
    let response: AxiosResponse<any, any>
    useEffect(() => {
        if (called.current) return;
        called.current = true;

        const fetchTrips = async () => {
            try {
                response = await api.get('api/trip/', {
                    headers: { "Authorization": `Bearer ${localStorage.getItem("authToken")}` }
                });
                console.log(response.data);
            } catch (error) {
                console.error('Error fetching trips:', error);
            }
        };
        fetchTrips();
    }, []);

    return (
        <div className="container">
            {response.data.map((trip) => (
                <p>{trip.title}</p>
            ))}
        </div>
    );
}

export default DisplayTrips;