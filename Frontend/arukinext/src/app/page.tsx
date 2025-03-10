import Image from "next/image";
import BasePage from "./BasePage";


export default function Home() {
  return (
    <div className = "flex flex-col h-screen w-screen bg-red-600">
        <BasePage />
    </div>
  );
}
