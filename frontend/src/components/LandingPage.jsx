import React from 'react'
import Card from './Card';
import { motion } from 'framer-motion';
import { useNavigate } from 'react-router-dom';

const desc = "Generate short, memorable links with ease using Shortty urls intuitive interface. Share URLs effortlessly across platforms. Optimize your sharing strategy with Shortty url. Track clicks and manage your links seamlessly to enhance your online presence. Generate short, memorable links with ease using Shortty urls intuitive interface. Share URLs effortlessly across platforms.";

export const LandingPage = () => {
  const navigate = useNavigate();

  const dashboardNavigationHandler = () => {

  };

  return (
    <div className="min-h-[calc(100vh-64px)] lg:px-14 sm:px-8 px-4">
      <div className="lg:flex-row flex-col lg:py-5 pt-16 lg:gap-10 gap-8 flex justify-between items-center">
        <div className="flex-1">
          <motion.h1
            initial={{ opacity: 0, y: -80 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            transition={{ duration: 0.8 }}
            className="font-bold font-roboto text-slate-800 md:text-5xl sm:text-4xl text-3xl   md:leading-[55px] sm:leading-[45px] leading-10 lg:w-full md:w-[70%] w-full">
            Shortty url shortener is a tool to manage and shorten your long urls.
          </motion.h1>
          <motion.p
            initial={{ opacity: 0, x: -80 }}
            whileInView={{ opacity: 1, x: 0 }}
            viewport={{ once: true }}
            transition={{ duration: 0.8 }}
            className="text-slate-700 text-sm my-5">
            Shortty url streamlines the process of URL shortening, making sharing
            links effortless and efficient. With its user-friendly interface,
            Shortty url allows you to generate concise, easy-to-share URLs in
            seconds. Simplify your sharing experience with Shortty url today.
          </motion.p>

          <div className='flex items-center gap-3'>
            <motion.button
              initial={{ opacity: 0, y: 80 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.8 }}
              className='bg-custom-gradient w-40 text-white rounded-md py-2'>
              Manage Links
            </motion.button>

            <motion.button
              initial={{ opacity: 0, y: 80 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.8 }}
              className='border-btnColor border w-40 text-btnColor rounded-md py-2'
              onClick={() => dashboardNavigationHandler()}>
              Create Short Links
            </motion.button>
          </div>
        </div>

        <div className='flex-1 flex justify-center w-full'>
          <motion.img
            initial={{ opacity: 0, x: -50 }}
            whileInView={{ opacity: 1, x: 0 }}
            viewport={{ once: true }}
            transition={{ duration: 1 }}
            className='sm:w-[480px] w-[480px] h-[400px] object-cover rounded-md'
            src="/images/shortty-urls-1.png" alt="main image" />
        </div>
      </div>

      <div className='sm:pt-12 pt-7'>
        <motion.p
          initial={{ opacity: 0, y: 80 }}
          whileInView={{ opacity: 1, y: 0 }}
          viewport={{ once: true }}
          transition={{ duration: 0.8 }}
          className='text-slate-800 font-roboto font-bold lg:w-[60%] md:w-[70%] sm:w-[80%] mx-auto text-3xl text-center'>
          Trusted by individuals and teams across the world.
        </motion.p>

        <div className="pt-4 pb-7 grid lg:gap-7 gap-4 xl:grid-cols-4  lg:grid-cols-3 sm:grid-cols-2 grid-cols-1 mt-4">
          <Card
            title="Simple URL Shortening"
            desc="Experience the ease of creating short, memorable URLs in just a few clicks. Our intuitive interface and quick setup process ensure you can start shortening URLs without any hassle."
          />
          <Card
            title="Powerful Analytics"
            desc="Gain insights into your link performance with our comprehensive analytics dashboard. Track clicks, geographical data, and referral sources to optimize your marketing strategies."
          />
          <Card
            title="Enhanced Security"
            desc="Rest assured with our robust security measures. All shortened URLs are protected with advanced encryption, ensuring your data remains safe and secure."
          />
          <Card
            title="Fast and Reliable"
            desc="Enjoy lightning-fast redirects and high uptime with our reliable infrastructure. Your shortened URLs will always be available and responsive, ensuring a seamless experience for your users."
          />
        </div>
      </div>
    </div>
  )
}
