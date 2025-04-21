import React, { useState } from 'react'
import { useForm } from 'react-hook-form'
import TextField from './TextField';
import { Link, useNavigate } from 'react-router-dom';
import api from '../api/api';
import toast from 'react-hot-toast';

const RegisterPage = () => {
  const navigate = useNavigate();
  const [loader, setLoader] = useState(false);

  const { register, handleSubmit, reset, formState: { errors } } = useForm({
    defaultValues: {
      username: "",
      email: "",
      password: "",
    },
    mode: "onTouched",
  });

  const registerHandler = async (data) => {
    try {
      setLoader(true);
      const { data: response } = api.post("/api/auth/public/register", data);
      console.log(response);
      reset();
      toast.success("Registration successful!")
      navigate("/login");
    } catch (error) {
      toast.error("Registration failed!")
      console.error(error);
    } finally {
      setLoader(false);
    }
  }

  return (
    <div
      className='min-h-[calc(100vh-64px)] flex justify-center items-center'
    >
      <form onSubmit={handleSubmit(registerHandler)}
        className='s:w-[450px] w-[350px] shadow-custom py-8 sm:px-8 px-4 rounded-md'
      >
        <h1
          className='text-center font-serif text-btnColor font-bold lg:text-3xl text-2xl'
        >Register Here</h1>

        <hr className='mt-2 mb-5 text-black' />

        <div className='flex flex-col gap-3'>
          <TextField
            required
            id="username"
            label="Username"
            type="text"
            message="*Username is required"
            placeholder="Enter your username"
            register={register}
            errors={errors}
          />

          <TextField
            required
            id="email"
            label="Email"
            type="email"
            message="*Email is required"
            placeholder="Enter your Email"
            register={register}
            errors={errors}
          />

          <TextField
            required
            id="password"
            label="Password"
            type="password"
            message="*Password is required"
            placeholder="Enter your Password"
            register={register}
            min={6}
            errors={errors}
          />
        </div>

        <button
          type='submit'
          disabled={loader}
          className='bg-customRed font-semibold text-white  bg-custom-gradient w-full py-2 hover:text-slate-400 transition-colors duration-100 rounded-sm my-3'>
          {loader ? "Loading..." : "Register"}
        </button>

        <p className='text-center text-sm mt-4 text-slate-700'>
          Already have an account?
          <Link className='font-semibold hover:text-black'
            to="/login">
            <span className='text-btnColor ml-1'>Login</span>
          </Link>
        </p>
      </form>
    </div>
  )
}

export default RegisterPage