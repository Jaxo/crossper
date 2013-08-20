db.offers.aggregate( [ 
{$unwind : '$scannedOffers' },
{$match: {'scannedOffers.userId' : "51a6ee56594c83c540ded0f9"}} 
]);


