import { useContext } from 'react';
import { styled } from 'styled-components';
import { AppContext } from '../main';
import ContextLogo from '../types/ContextLogo';
import TextInput from '../components/common/TextInput';
// type TextInputProps = React.HTMLAttributes<HTMLInputElement> & {
//   size: 'tall' | 'short';
//   labelName: string;
//   disabled?: boolean;
//   placeholder?: string;
//   helpText?: string;
//   validationFunc?: (value: string) => boolean;
// };
export default function Login() {
  const appContext = useContext(AppContext);
  const logo = (appContext.util.getLogoByTheme() as ContextLogo)
    .large as string;
  return (
    <Container>
      <h1 className="blind">로그인 페이지</h1>
      <figure>
        <img src={logo} alt="" />
        <figcaption className="blind">이슈트래커</figcaption>
      </figure>
      <form>
        <TextInput size='tall' labelName='아이디'></TextInput>
      </form>
    </Container>
  );
}

const Container = styled.article`
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
`;
