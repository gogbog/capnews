import React, { useState } from "react";
// import styles of this component
import styles from "./Header.module.css"

// import other components
import ContainerCard from '../ContainerCard/ContainerCard';
import Nav from "../Nav/Nav"

// import something from react packages
import { ClipboardText, Crown, Image } from "iconsax-react";
import { Setting4 } from "iconsax-react";

import { useAuth } from "react-oidc-context";
import axios from "axios";
import toast from "react-hot-toast";

// Header component
const Header = () => {
    const auth = useAuth();
    console.log(auth);

    const hiddenFileInput = React.useRef(null);
    const [fileName, setFileName] = useState(null);

    const headers = {
        Accept: "application/json",
        Authorization: "Bearer " + auth.user?.access_token
    };



    // Programatically click the hidden file input element
    // when the Button component is clicked
    const handleFileClick = () => {
        hiddenFileInput.current.click();
    };

    const handleFileChange = (event) => {
        const uploadedFile = event.target.files[0];
        const truncatedFileName = uploadedFile.name.substring(0, 47);
        setFileName(truncatedFileName);
    };



    const handleSubmit = (event) => {

        event.preventDefault();
        const formData = new FormData(event.target);
        //formData.append("file", file);
        //

        axios.post('http://localhost:8083/articles', formData, { headers })
            .then(response => {
                toast.success("Congratulations!\nYour art is now in the digital realm!");
                setFileName(null);
                event.target.reset()
            })
            .catch(error => {
                toast.error("Something went wrong");
            });

    };


    return (
        <header className={`${styles.header} flex justify-content-center`}>
            <ContainerCard className="flex flex-column">
                <div className={styles["blur-circle-shape"]}></div>

                <Nav />
                {
                    //*<BrickLayout />
                }

                <div className={`${styles["headings-header"]} flex justify-content-center flex-column `}>
                    {auth.isAuthenticated &&
                        <h2 className={styles["heading-header-title"]}>Hello {auth.user?.profile.name}😎🤘</h2>
                    }
                    <h1 className={styles["heading-header-second-title"]}>
                        Artists make The Arts better <br />
                        The Arts design the <span>world</span> better
                    </h1>

                    <form onSubmit={handleSubmit} >
                        <div className={`${styles["search-bar"]} flex align-items-center`}>
                            <Crown size="30" color="var(--white-100)" />
                            <input name="title" type="text" className={styles["search-input"]} placeholder="Choose a name for your creation" />
                        </div>

                        <div className={`${styles["search-bar"]} flex align-items-center`}>
                            <ClipboardText size="30" color="var(--white-100)" />
                            <input name="content" type="text" className={styles["search-input"]} placeholder="Small description about your creation" />
                        </div>

                        <div className={`${styles["search-bar"]} flex align-items-center`} htmlFor="img">
                            <Image size="30" color="var(--white-100)" />

                            <span className={`${styles["inputTextSpan"]}`}>{fileName || "Select your maginificent image"}</span>
                            <input name="file" ref={hiddenFileInput} onChange={handleFileChange} type="file" className={styles["search-input"]} placeholder={fileName || "Upload a file"} id="img" style={{ opacity: "0", cursor: "pointer" }} />
                            <button onClick={handleFileClick} className={`${styles["search-btn"]} flex justify-content-center align-items-center`}>
                                <Setting4 size="20" color="var(--dark-900)" />
                            </button>

                        </div>

                        <button type="submit" className={`${styles["search-bar"]} ${styles["submitButton"]} flex align-items-center`} style={{ cursor: "pointer" }}>
                            <span>Make your creation come to life</span>
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

export default Header
