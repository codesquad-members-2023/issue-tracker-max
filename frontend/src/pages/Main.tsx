import { useContext } from 'react';
import { AppContext } from '../main';

export default function Main() {
  const { control } = useContext(AppContext);
  return (
    <>
      <h1>main</h1>
      <button onClick={() => control.logoutCheck()}>logout</button>
    </>
  );
}
