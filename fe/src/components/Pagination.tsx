import chevronDown from "@assets/icon/chevronDown.svg";
import styled from "styled-components";
import Button from "./common/Button";

export default function Pagination({
  currentPage,
  totalPages,
  onPageChange,
  onPrevPageClick,
  onNextPageClick,
}: {
  currentPage: number;
  totalPages: number;
  onPageChange: (page: number) => void;
  onPrevPageClick: () => void;
  onNextPageClick: () => void;
}) {
  const pageIndexList: number[] = Array.from(
    { length: totalPages },
    (_, i) => i + 1
  );
  const isNextButtonDisabled = currentPage === totalPages || totalPages === 0;
  const isPrevButtonDisabled = currentPage === 1;

  return (
    <StyledPagination>
      <StyledPaginationItem>
        <Button
          variant="ghost"
          size="S"
          disabled={isPrevButtonDisabled}
          onClick={onPrevPageClick}>
          <img
            src={chevronDown}
            className="previous-page-btn"
            alt="previous-page-btn"
          />
        </Button>
      </StyledPaginationItem>
      {pageIndexList.map((pageIndex) => {
        return (
          <StyledPaginationItem
            $active={pageIndex === currentPage}
            key={pageIndex}>
            <Button
              variant="ghost"
              size="S"
              onClick={() => onPageChange(pageIndex)}>
              {pageIndex}
            </Button>
          </StyledPaginationItem>
        );
      })}
      <StyledPaginationItem>
        <Button
          variant="ghost"
          size="S"
          disabled={isNextButtonDisabled}
          onClick={onNextPageClick}>
          <img
            src={chevronDown}
            className="next-page-btn"
            alt="next-page-btn"
          />
        </Button>
      </StyledPaginationItem>
    </StyledPagination>
  );
}

const StyledPagination = styled.ul`
  display: flex;
  width: 100%;
  justify-content: center;
  gap: 1rem;
`;

const StyledPaginationItem = styled.li<{ $active?: boolean }>`
  min-width: 2rem;
  height: 2rem;
  border-radius: ${({ theme: { radius } }) => radius.m};
  background-color: ${({ $active, theme: { brand } }) =>
    $active ? brand.surface.default : "transparent"};
  color: ${({ $active, theme: { neutral } }) =>
    $active ? neutral.surface.strong : neutral.text.default};

  button {
    width: 100%;
    height: 100%;

    &:hover:not(:disabled) {
      border: ${({ $active, theme: { border, neutral } }) =>
        !$active && `${border.default} ${neutral.border.default}`};
      border-radius: ${({ theme: { radius } }) => radius.m};
    }
  }

  .previous-page-btn {
    transform: rotate(90deg);
  }

  .next-page-btn {
    transform: rotate(270deg);
  }
`;
