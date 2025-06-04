import type { AxiosResponse } from 'axios';
import api from '../api/axios.ts';
import { useEffect, useRef, useState } from 'react';

interface Trip {
    title: string;
    // Other fields as needed
}

function DisplayTrips() {
    const called = useRef(false);
    const [response, setResponse] = useState<AxiosResponse<{ data: Trip[] }> | undefined>(undefined);

    useEffect(() => {
        if (called.current) return;
        called.current = true;

        const fetchTrips = async () => {
            try {
                const responseTemp = await api.get('api/trip/', {
                    headers: { "Authorization": `Bearer ${localStorage.getItem("authToken")}` }
                });
                setResponse(responseTemp);
            } catch (error) {
                console.error('Error fetching trips:', error);
            }
        };
        fetchTrips();
    }, []);

    return (
        <div className="container">
            {response?.data?.map((trip: Trip, idx: number) => (
                <p key={idx}>{trip.title}</p>
            ))}
        </div>
    );
}

export default DisplayTrips;