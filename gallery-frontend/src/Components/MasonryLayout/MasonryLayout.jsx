// import styles of this component
import styles from "./MasonryLayout.module.css"

// import other react pkg to use
import Masonry from "react-masonry-css"

// import other component to use
import MasonryBox from './MasonryBox/MasonryBox';

// MasonryLayout Component
const MasonryLayout = ({ images }) => {
    const breakpointColumnsObj = {
        default: 4,
        1100: 3,
        700: 2,
        500: 1
    };

    return (
        <Masonry
            breakpointCols={breakpointColumnsObj}
            className={styles["my-masonry-grid"]}
            columnClassName={styles["my-masonry-grid_column"]}
        >
            {images.map(item => (
                <MasonryBox
                    key={item.id}
                    wallSrc={item.image}
                    userProf={"https://images.squarespace-cdn.com/content/v1/5c538536ab1a62308aa5d25c/e3d5e6cb-63d6-4f14-84a5-814dfe5a6efb/true.gif"}
                    userName={item.firstName + " " + item.lastName}
                    userJob={"Web Developer"}
                />
            ))}
        </Masonry>
    )
}

export default MasonryLayout
