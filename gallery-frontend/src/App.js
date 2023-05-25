// import other components to use
import Header from './Components/Header/Header';

import { AuthProvider } from "react-oidc-context";
import { BrowserRouter, Route, Router, Routes } from "react-router-dom";
import Index from "./Layouts/Index";
import { Toaster } from 'react-hot-toast';
import Settings from './Components/Header/Settings';


// App component
const App = () => {

    const oidcConfig = {
        authority: "http://keycloak:8080/auth/realms/my_realm/",
        client_id: "frontend_client",
        redirect_uri: "http://localhost:3000/",
        response_type: 'code',
        scope: "openid profile message.read",
    };

    return (
        <AuthProvider {...oidcConfig}>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<Header />} />
                    <Route path="/explore" element={<Index />} />
                    <Route path="/settings" element={<Settings />} />
                </Routes>
                <Toaster
                    position="top-center"
                    reverseOrder={false}
                    gutter={8}
                    containerClassName=""
                    containerStyle={{}}
                    toastOptions={{
                        // Define default options
                        className: '',
                        duration: 5000,
                        style: {
                            background: '#363636',
                            color: '#fff',
                            textAlign: "center"
                        },

                        // Default options for specific types
                        success: {
                            duration: 9000,
                            theme: {
                                primary: 'green',
                                secondary: 'black',
                            },
                        },
                    }}
                />

            </BrowserRouter>
        </AuthProvider>
    )
}

export default App
