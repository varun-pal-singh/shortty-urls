import React from "react";
import { FaFacebook, FaTwitter, FaInstagram, FaLinkedin } from "react-icons/fa";

const Footer = () => {
    return (
        <footer className="bg-custom-gradient text-white py-8 z-40 relative">
            <div className="container mx-auto px-6 lg:px-14 flex flex-col lg:flex-row lg:justify-between items-center gap-4">
                <div className="text-center lg:text-left">
                    <h2 className="text-3xl font-bold mb-2">Shortty Urls</h2>
                    <p>Simplifying URL shortening for efficient sharing</p>
                </div>

                <p className="mt-4 lg:mt-0">
                    &copy; 2024 Shortty Urls. All rights reserved.
                </p>ś

                <div className="flex space-x-6 mt-4 lg:mt-0">
                    <a href="https://www.linkedin.com/in/varun-pal-singh/" className="hover:text-gray-200">
                        <FaFacebook size={24} />
                    </a>
                    <a href="https://www.linkedin.com/in/varun-pal-singh/" className="hover:text-gray-200">
                        <FaTwitter size={24} />
                    </a>
                    <a href="https://www.linkedin.com/in/varun-pal-singh/" className="hover:text-gray-200">
                        <FaInstagram size={24} />
                    </a>
                    <a href="https://www.linkedin.com/in/varun-pal-singh/" className="hover:text-gray-200">
                        <FaLinkedin size={24} />
                    </a>
                </div>
            </div>
        </footer>
    );
};

export default Footer;