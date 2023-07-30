import { Outlet } from 'react-router-dom';
type Props = {
  resetUserId?: () => void;
  resetAccessToken?: () => void;
  toggleTheme?: () => void;
};

export const Header: React.FC = ({}: Props) => {
  return (
    <>
      <div>Header</div>
      <Outlet />
    </>
  );
};
