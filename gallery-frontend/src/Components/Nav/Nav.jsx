// import styles of this component
import styles from "./Nav.module.css"

// import other components
import Button from "../Elements/Button/Button"

// import other react pkg to use
import { HambergerMenu } from "iconsax-react"
import { useAuth } from "react-oidc-context"
import { Link, useLocation } from "react-router-dom"

// Nav component
const Nav = () => {

    const auth = useAuth();
    const location = useLocation();
    console.log("TOKEN" + auth.user?.id_token);



    return (
        <nav className={`${styles.nav} flex align-items-center`}>
            <h1 className={styles["nav-title"]}>CAPNEWS</h1>
            <ul className={`flex align-items-center ${styles["navbar-nav"]}`}>
                <li className={`${styles["nav-item"]} ${location.pathname === '/' ? styles.active : ''}`}>
                    <Link to="/" className={styles["nav-link"]}>Home</Link>
                </li>
                <li className={`${styles["nav-item"]} ${location.pathname === '/explore' ? styles.active : ''}`}>
                    <Link to="/explore" className={styles["nav-link"]}>Explore</Link>
                </li>
                {auth.isAuthenticated &&
                    <li className={`${styles["nav-item"]} ${location.pathname === '/settings' ? styles.active : ''}`}>
                        <Link to="/settings" className={styles["nav-link"]}>Settings</Link>
                    </li>
                }
            </ul>
            {!auth.isAuthenticated &&
                <>
                    <div className={`flex ${styles["navbar-buttons"]}`}>
                        <Button theme="matrix" onClick={auth.signinRedirect}>Login</Button>
                    </div>
                </>
            }

            {auth.isAuthenticated &&
                <div className={`flex ${styles["navbar-buttons"]}`}>
                    <Button theme="matrix" onClick={auth.signoutSilent}>Logout</Button>
                </div>
            }
            <div className={styles["navbar-responsive-menu"]}>
                <Button theme="transparent">
                    <HambergerMenu size="32" color="var(--white-100)" />
                </Button>
            </div>

        </nav>
    )
}

export default Nav
