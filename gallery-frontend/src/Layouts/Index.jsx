import axios from "axios";
import { useState } from "react";
import ContainerCard from "../Components/ContainerCard/ContainerCard";
import styles from "../Components/Header/Header.module.css"
import MasonryLayout from "../Components/MasonryLayout/MasonryLayout";
import Nav from "../Components/Nav/Nav";


const Index = () => {

    const [data, setData] = useState(null);

    if (data == null) {
        axios.get('http://localhost:8083/articles')
            .then(response => {
                setData(response.data);
                console.log(data);
            })
            .catch(error => {
                console.log(error);
            });
    }

    return (
        <>
            <header className={`${styles.header} flex justify-content-center`} style={{ height: 127 }}>
                <ContainerCard className="flex flex-column">
                    <Nav />
                </ContainerCard>
            </header>

            <div className="flex justify-content-center" style={{ marginTop: "50px", padding: '50px' }}>
                {data ? (
                    <ContainerCard>
                        <div className={`${styles["gallery-setting"]} flex justify-content-between align-items-center`}>
                            <h1>All images</h1>
                        </div>
                        <MasonryLayout images={data} />
                    </ContainerCard>
                ) : (
                    <p> Loading Data </p>
                )
                }
            </div>
        </>

    )
}

export default Index
