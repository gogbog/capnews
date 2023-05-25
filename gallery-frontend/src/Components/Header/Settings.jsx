// import styles of this component
import styles from "./Header.module.css"

// import other components
import ContainerCard from '../ContainerCard/ContainerCard';
import Nav from "../Nav/Nav"

// import something from react packages
import { Crown } from "iconsax-react";

import { useAuth } from "react-oidc-context";
import axios from "axios";
import toast from "react-hot-toast";
import { useEffect, useState } from "react";

// Header component
const Settings = () => {
    const auth = useAuth();

    const headers = {
        Accept: "application/json",
        Authorization: "Bearer " + auth.user?.access_token,
        'Content-Type': 'application/json',
    };

    const [userData, setUserData] = new useState();

    // Similar to componentDidMount and componentDidUpdate:
    useEffect(() => {
        if (auth.user) {
            setUserData(auth.user.profile);
        }
    }, [auth.user]);
    const handleSubmit = (event) => {

        event.preventDefault();

        const formData = new FormData(event.target);
        // Send PUT request to update user profile
        axios
            .post(
                'http://keycloak:8080/auth/realms/my_realm/account',
                formData,
                { headers }
            )
            .then((response) => {
                toast.success("User profile updated successfully!");
                auth.user.profile = { ...auth.user.profile,
                    name: event.target.elements.firstName.value + " " + event.target.elements.lastName.value,
                    given_name: event.target.elements.firstName.value,
                    family_name: event.target.elements.lastName.value,
                }
                setUserData(auth.user.profile);
                event.target.reset()
                // Handle success response
            })
            .catch((error) => {
                toast.error("Error updating user profile:", error);
                // Handle error response
            });
    };


    return (
        <header className={`${styles.header} flex justify-content-center`}>
            <ContainerCard className="flex flex-column">
                <div className={styles["blur-circle-shape"]}></div>

                <Nav />

                <div className={`${styles["headings-header"]} flex justify-content-center flex-column `}>
                    <h1 className={styles["heading-header-second-title"]}>
                        Change Your <span>Profile</span>
                    </h1>

                    <form onSubmit={handleSubmit} >
                        <div className={`${styles["search-bar"]} flex align-items-center`}>
                            <Crown size="30" color="var(--white-100)" />
                            <input name="firstName" type="text" className={styles["search-input"]} placeholder="First Name" defaultValue={(userData) ? userData.given_name : ""} />
                        </div>
                        <div className={`${styles["search-bar"]} flex align-items-center`}>
                            <Crown size="30" color="var(--white-100)" />
                            <input name="lastName" type="text" className={styles["search-input"]} placeholder="Last Name" defaultValue={(userData) ? userData.family_name : ""} />
                        </div>
                        <div className={`${styles["search-bar"]} flex align-items-center`}>
                            <Crown size="30" color="var(--white-100)" />
                            <input name="email" type="text" className={styles["search-input"]} placeholder="Email" defaultValue={(userData) ? userData.email : ""} />
                        </div>



                        <button type="submit" className={`${styles["search-bar"]} ${styles["submitButton"]} flex align-items-center`} style={{ cursor: "pointer" }}>
                            <span>Update My Profile</span>
                        </button>


                    </form>

                    {
                        //*<HeaderBoxes titles_numbers={JsonHeader.informations} /> 
                    }
                </div>
            </ContainerCard>
        </header>
    )
}

export default Settings;
